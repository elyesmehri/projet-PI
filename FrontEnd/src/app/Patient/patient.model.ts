import {Doctor} from "../Doctor/doctor.model";


export interface Patient {
  id: number;
  patientName: string;
  doctorname : Doctor;
  age: number;
  gender: boolean;
  medicalCondition: string ,
  address: string;
  insurance: string ,
  phoneNumber: string;
  medical_state : string;
  password : string;
  about_me: string;
}
