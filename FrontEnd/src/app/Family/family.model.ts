import {Patient} from "../Patient/patient.model";

export interface FamilyRequest {

  id : number;
  familyname: string;
  relative: string;
  relationship : string;
  address : string;
  phoneNumber : string;
  insurance : string;
  invest : number;
  password : string;
  advice : string;
  login : number;
}


export interface CheckFamilyRequest {

  id: number;
  relative : string;
  password : string;
}

export interface Family {

    id : number;
    familyname: string;
    relative: string;
    relationship : string;
    address : string;
    phoneNumber : string;
    insurance : string;
    invest : number;
    password : string;
    advice : string;
    login : number;

    patients: Patient[];
}

export interface UpdatePasswordRequest {
  newPassword: string;
}
