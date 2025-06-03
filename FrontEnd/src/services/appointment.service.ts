import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import { Observable } from "rxjs";
import {AppointmentRequest, AppointmentResponse} from '../app/Appointment/appointment.model'

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private baseUrl = 'http://localhost:8082/appointment';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };


  constructor(private http: HttpClient) {}

// Create Appointment
  createAppointment(appointment: {
    familyId: number;
    doctorId: number;
    date: Date;
    description: string;
    emergency: number
  }): Observable<AppointmentResponse> {
    return this.http.post<AppointmentResponse>(`${this.baseUrl}/create`, appointment, this.httpOptions);
  }

  // Get All Appointments
  getAllAppointments(): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.baseUrl}/appointment`);
  }

  // Get Appointment by ID
  getAppointmentById(id: number): Observable<AppointmentResponse> {
    return this.http.get<AppointmentResponse>(`${this.baseUrl}/appointment/${id}`);
  }

  // Update Appointment
  updateAppointment(id: number, appointment: AppointmentRequest): Observable<AppointmentResponse> {
    return this.http.put<AppointmentResponse>(`${this.baseUrl}/appointment/${id}`, appointment, this.httpOptions);
  }

  // Delete Appointment
  deleteAppointment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/appointment/${id}`);
  }

  // Remove Doctor from Appointment (sets Appointment's doctor_id to NULL directly)
  removeDoctorFromAppointment(appointmentId: number): Observable<AppointmentResponse> {
    return this.http.patch<AppointmentResponse>(`${this.baseUrl}/appointment/${appointmentId}/removeDoctor`, null);
  }

}

