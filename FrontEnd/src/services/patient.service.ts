import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { BehaviorSubject, catchError, map, Observable, of, tap } from "rxjs";
import { Patient, PatientRequest } from "../app/Patient/patient.model";
import { UpdatePasswordRequest } from "../app/Doctor/doctor.model";
import { CheckPatientRequest } from "../app/Patient/patient.model";

interface AuthResponse {
  success?: boolean;
  error?: string;
  //  You might get a token or user info here on success
  //  token?: string;
  //  userId?: number;
}


class UpdatePasswordPayload {
}


class PatientUpdateRequest {
}

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = 'http://localhost:8082/patient';
  private currentPatientNameSubject = new BehaviorSubject<string>(''); // BehaviorSubject
  public currentPatientName$ = this.currentPatientNameSubject.asObservable(); // Observable
  private patientName: string | undefined;

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
  }

  getCurrentPatientName(): string {
    return this.currentPatientNameSubject.value;
  }

  setPatientName(patientName: string): void {
    this.currentPatientNameSubject.next(patientName);
    console.log(`Family name set in service: ${patientName}`);
  }

  createPatient(patient: {
    patientName: string;
    age: number;
    gender: boolean;
    address: string;
    phoneNumber: string;
    password: string;
    medical_state: string
  }): Observable<Patient> {
    return this.http.post<Patient>(`${this.baseUrl}/create`, patient, this.httpOptions);
  }

  // Get All Patients (assuming this endpoint exists)
  getAllPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/all`);
  }

  // Get Patient by ID (assuming this endpoint exists)
  getPatientById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.baseUrl}/${id}`);
  }

  // Delete Patient (assuming this endpoint exists)
  deletePatient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Assign Family to Patient (One-to-Many, Patient owns FK)
  assignFamilyToPatient(patientId: number, familyId: number): Observable<Patient> {
    return this.http.patch<Patient>(`${this.baseUrl}/${patientId}/assignFamily/${familyId}`, null);
  }

  // Remove Family from Patient (One-to-Many, sets Patient's family_id to NULL)
  removeFamilyFromPatient(patientId: number): Observable<Patient> {
    return this.http.patch<Patient>(`${this.baseUrl}/${patientId}/removeFamily`, null);
  }

  // Update Patient Password
  updatePatientPassword(patientId: number, newPassword: string): Observable<string> {
    // 1. Create the request body object
    const requestBody: UpdatePasswordRequest = {newPassword: newPassword};

    // 2. Construct the URL to match the secure backend endpoint: /api/doctors/{id}/password
    const url = `${this.baseUrl}/password/update/${patientId}?password=${encodeURIComponent(newPassword)}`;

    // 3. Send the PATCH request with the body
    return this.http.patch<string>(url, requestBody, this.httpOptions);
  }

  // Get Family id by his name
  getPatientIdByName(patientName: string): Observable<number> {
    // Note: Use encodeURIComponent if the name might contain special characters or spaces
    return this.http.get<number>(`${this.baseUrl}/id-by-name?name=${encodeURIComponent(patientName)}`);
  }

  updatePatient(id: number, updateData: PatientUpdateRequest): Observable<Patient> {
    const url = `${this.baseUrl}/${id}`; // Constructs the URL like http://localhost:8082/patient/123
    console.log(`Sending PUT request to: ${url} with data:`, updateData);
    return this.http.patch<Patient>(url, updateData, this.httpOptions);
  }

  // Check Patient Exists (login-like functionality)
  checkPatientExists(credentials: CheckPatientRequest): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseUrl}/auth`, credentials, this.httpOptions);
  }

}

