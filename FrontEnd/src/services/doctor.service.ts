import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {BehaviorSubject, catchError, map, Observable, of, tap} from 'rxjs';
import {CheckDoctorRequest, Doctor, DoctorRequest, UpdatePasswordRequest} from '../app/Doctor/doctor.model';
import {AppointmentResponse} from "../app/Appointment/appointment.model";


interface AuthResponse {
  success?: boolean;
  error?: string;
  //  You might get a token or user info here on success
  //  token?: string;
  //  userId?: number;
  doctorName?: string;
}

class UpdatePasswordPayload {
}

@Injectable({
  providedIn: 'root'
})

export class DoctorService {

  private baseUrl = 'http://localhost:8082/doctor';
  private currentDoctorNameSubject = new BehaviorSubject<string>(''); // BehaviorSubject
  public currentDoctorName$ = this.currentDoctorNameSubject.asObservable(); // Observable

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  getCurrentDoctorName(): string {
    return this.currentDoctorNameSubject.value;
  }

  setDoctorName(doctorName: string): void {
    this.currentDoctorNameSubject.next(doctorName);
    console.log(`Doctor name set in service: ${doctorName}`);
  }

  updateDoctorPassword(doctorId: number, newPassword: string): Observable<string> {
    // 1. Create the request body object
    const requestBody: UpdatePasswordRequest = { newPassword: newPassword };

    // 2. Construct the URL to match the secure backend endpoint: /api/doctors/{id}/password
    const url = `${this.baseUrl}/password/update/${doctorId}?password=${encodeURIComponent(newPassword)}`;

    // 3. Send the PATCH request with the body
    return this.http.patch<string>(url, requestBody, this.httpOptions);
  }

  // Create Doctor
  createDoctor(doctor: {

    doctorname: string;
    medicalID: string;
    password: string;
    speciality: string;
    address: string;
    phonenumber: string;
    hospital: string;
    gender: boolean
  }): Observable<Doctor> {
    return this.http.post<Doctor>(`${this.baseUrl}/create`, doctor, this.httpOptions);
  }

  // Get All Doctors
  getAllDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.baseUrl}/all`);
  }

  // Get Doctor by ID
  getDoctorById(id: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.baseUrl}/${id}`);
  }

  /*
  // Update Doctor
  updateDoctor(id: number, doctor: DoctorRequest): Observable<Doctor> {
    return this.http.put<Doctor>(`${this.baseUrl}/${id}`, doctor, this.httpOptions);
  }

  // Delete Doctor
  deleteDoctor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/doctor/${id}`);
  }
*/

  // Add Family to Doctor (Many-to-Many)
  addFamilyToDoctor(Id: number, familyId: number): Observable<Doctor> {
    return this.http.post<Doctor>(`${this.baseUrl}/${Id}/families/${familyId}`, null);
  }
  // Add Patient to Doctor (Many-to-Many)
  addPatientToDoctor(Id: number, patientId: number): Observable<Doctor> {
    return this.http.post<Doctor>(`${this.baseUrl}/${Id}/patients/${patientId}`, null);
  }

  // Remove Patient from Doctor (Many-to-Many)
  removePatientFromDoctor(Id: number, patientId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${Id}/patients/${patientId}`);
  }

  // Assign Appointment to Doctor (One-to-Many, Appointment owns FK)
  assignAppointmentToDoctor(Id: number, appointmentId: number): Observable<Doctor> {
    return this.http.post<Doctor>(`${this.baseUrl}/${Id}/appointments/${appointmentId}`, null);
  }

  // Remove Appointment from Doctor (One-to-Many, sets Appointment's doctor_id to NULL)
  removeAppointmentFromDoctor(Id: number, appointmentId: number): Observable<Doctor> {
    return this.http.delete<Doctor>(`${this.baseUrl}/${Id}/appointments/${appointmentId}`);
  }

  // Get Doctor with their associated Families (if backend handles it)
  getDoctorWithFamilies(id: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.baseUrl}/${id}/families`);
  }

  // Get all Appointments for a specific Doctor
  getDoctorAppointments(doctorId: number): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.baseUrl}/${doctorId}/appointments`);
  }

  // Get Doctor id by his name
  getDoctorIdByName(doctorName: string): Observable<number> {
    // Note: Use encodeURIComponent if the name might contain special characters or spaces
    return this.http.get<number>(`${this.baseUrl}/id-by-name?name=${encodeURIComponent(doctorName)}`);
  }

  // Check Doctor Exists (login-like functionality)
  checkDoctorExists(credentials: CheckDoctorRequest): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseUrl}/auth`, credentials, this.httpOptions);
  }
}
