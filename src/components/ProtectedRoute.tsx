import { Navigate } from 'react-router-dom';
import { ReactNode } from 'react';
import { api } from '../lib/api';

interface ProtectedRouteProps {
  children: ReactNode;
}

export default function ProtectedRoute({ children }: ProtectedRouteProps) {
  if (!api.isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}