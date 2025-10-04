// Simple test script to check API
async function testAPI() {
    try {
        console.log('Testing backend connection...');
        
        // Test basic endpoint
        const testResponse = await fetch('http://localhost:8080/api/complaints/test');
        console.log('Test endpoint status:', testResponse.status);
        if (testResponse.ok) {
            const testData = await testResponse.json();
            console.log('Test response:', testData);
        }
        
        // Test complaints endpoint
        const complaintsResponse = await fetch('http://localhost:8080/api/complaints');
        console.log('Complaints endpoint status:', complaintsResponse.status);
        if (complaintsResponse.ok) {
            const complaintsData = await complaintsResponse.json();
            console.log('Complaints data:', complaintsData);
            console.log('Number of complaints:', complaintsData.length);
        } else {
            console.log('Complaints endpoint failed');
        }
        
    } catch (error) {
        console.error('API test failed:', error);
    }
}

testAPI();