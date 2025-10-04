// Script to populate sample data
async function populateData() {
    try {
        console.log('Populating sample data...');
        
        const response = await fetch('http://localhost:8080/api/complaints/populate');
        
        if (response.ok) {
            const result = await response.json();
            console.log('Success:', result.message);
        } else {
            console.log('Failed to populate data:', response.status);
        }
        
        // Now fetch and display the complaints
        console.log('\nFetching complaints...');
        const complaintsResponse = await fetch('http://localhost:8080/api/complaints');
        if (complaintsResponse.ok) {
            const complaints = await complaintsResponse.json();
            console.log(`Found ${complaints.length} complaints:`);
            complaints.forEach((complaint, index) => {
                console.log(`${index + 1}. ${complaint.title} - ${complaint.status} (${complaint.priority})`);
            });
        }
        
    } catch (error) {
        console.error('Error:', error.message);
    }
}

populateData();