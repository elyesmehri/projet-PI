export interface PatientRequest {
  patientName: string;
  age: number;
  gender: boolean;
  medicalCondition: string;
  address: string;
  phoneNumber: string;
  insurance: string;
  password?: string;
  medical_state: string;
  about_me: string;
  familyId?: number; // To link to a Family during creation/update
}

export interface Patient {
  id: number;
  patientName: string;
  age: number;
  gender: boolean;
  medicalCondition: string;
  address: string;
  phoneNumber: string;
  insurance: string;
  password?: string;
  medical_state: string;
  about_me: string;
  family?: { // Simplified representation, usually only ID or basic info
    id: number;
    familyname: string;
  };
}

export interface CheckPatientRequest {

  id: number;
  password : string;
}
