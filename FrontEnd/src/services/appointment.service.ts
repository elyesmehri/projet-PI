import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import { Observable } from "rxjs";
import { Appointment } from '../app/appointment/appointment.model'

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private baseUrl = 'http://localhost:8085/appointment';

  constructor(private http: HttpClient) {}

  BookAppointment(appointment: Appointment): Observable<Appointment> {
    // Log the full appointment for debugging

    console.log('Full appointment object:', appointment);

    // Send only selected fields to the backend
    return this.http.post<Appointment>(`${this.baseUrl}/create`, appointment);
  }
}

