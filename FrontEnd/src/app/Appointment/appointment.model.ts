export interface AppointmentRequest {
  doctorId: number;
  familyId: number;
  date: string; // Use string for LocalDateTime (e.g., "2025-06-15T10:00:00")
  period: number;
  tariff: number;
  nature: number;
  emergency: string;
  state: number;
  description: string;
}

export interface AppointmentResponse {
  id: number;
  doctorId: number | null;
  doctorName: string | null;
  familyId: number | null;
  familyName: string | null;
  date: string; // Use string for LocalDateTime
  period: number;
  tariff: number;
  nature: number;
  emergency: string;
  state: number;
  description: string;
}
