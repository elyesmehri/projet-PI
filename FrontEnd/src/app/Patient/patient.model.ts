export interface Medication {
  medicationID: number;
  medicationName: string;
  dosage: string;
  posology: string;  // Posologie du médicament
  category: string;
}

export interface Patient {
  id: number;
  patientName: string;
  age: number;
  gender: boolean;  // true = Male, false = Female
  medicalCondition: string;
  doctor: string;
  address: string;
  phoneNumber: string;
  insurance: string;
  visits: { [date: string]: string };
  medications: Medication[];
  medical_state : number;
}
