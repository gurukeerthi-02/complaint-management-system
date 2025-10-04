const API_BASE_URL = 'http://localhost:8080/api';

class ApiService {
  private async request(endpoint: string, options: RequestInit = {}) {
    const url = `${API_BASE_URL}${endpoint}`;
    const config: RequestInit = {
      headers: {
        'Content-Type': 'application/json',
        ...(localStorage.getItem('token') && { Authorization: `Bearer ${localStorage.getItem('token')}` }),
      },
      ...options,
    };

    const response = await fetch(url, config);
    
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.error || 'Request failed');
    }

    return response.json();
  }

  async login(email: string, password: string) {
    const response = await this.request('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    });
    
    localStorage.setItem('token', response.token);
    localStorage.setItem('user', JSON.stringify(response.user));
    return response;
  }

  async signup(email: string, password: string, fullName: string, phone?: string) {
    const response = await this.request('/auth/signup', {
      method: 'POST',
      body: JSON.stringify({ email, password, fullName, phone }),
    });
    
    localStorage.setItem('token', response.token);
    localStorage.setItem('user', JSON.stringify(response.user));
    return response;
  }

  async createComplaint(complaint: {
    title: string;
    description: string;
    category: string;
    location: string;
    priority?: string;
    imageUrl?: string;
  }) {
    return this.request('/complaints', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify(complaint),
    });
  }

  async getComplaints() {
    return this.request('/complaints');
  }

  async getMyComplaints() {
    return this.request('/complaints/my');
  }

  async updateComplaintStatus(id: string, status: string) {
    return this.request(`/complaints/${id}/status`, {
      method: 'PUT',
      body: JSON.stringify({ status }),
    });
  }

  async upvoteComplaint(id: string) {
    return this.request(`/complaints/${id}/upvote`, {
      method: 'PUT',
    });
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  isAuthenticated() {
    return !!localStorage.getItem('token');
  }

  getCurrentUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }
}

export const api = new ApiService();

export type Complaint = {
  id: string;
  title: string;
  description: string;
  category: string;
  location: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED';
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
  upvotes: number;
  imageUrl?: string;
  createdAt: string;
  updatedAt: string;
  userEmail: string;
};

export type Profile = {
  id: string;
  email: string;
  fullName: string;
  phone?: string;
};