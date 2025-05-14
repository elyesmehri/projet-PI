import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {BehaviorSubject, catchError, map, Observable, of, tap} from "rxjs";
import { Patient } from "../app/Patient/patient.model";

interface AuthResponse {
  success?: boolean;
  error?: string;
  //  You might get a token or user info here on success
  //  token?: string;
  //  userId?: number;
}


class UpdatePasswordPayload {
}

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = 'http://localhost:8085/patient';
  private currentPatientNameSubject = new BehaviorSubject<string>(''); // BehaviorSubject
  public currentPatientName$ = this.currentPatientNameSubject.asObservable(); // Observable


  constructor(private http: HttpClient) { }

  getAll(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/all`);
  }

<<<<<<< HEAD
  addOne(patient: {
    id : number;
=======
  addOne(doctor: {
>>>>>>> 2254f2f9aaa23548609a463cd46d74596f3847d0
    patientName: string;
    age: number;
    gender: boolean;
    address: string;
<<<<<<< HEAD
    phoneNumber: string;
=======
    phoneNumber: number;
>>>>>>> 2254f2f9aaa23548609a463cd46d74596f3847d0
    password: string;
    medical_state: string
  }): Observable<Patient> {
    return this.http.post<Patient>(`${this.baseUrl}/add`, patient);
  }

  Login(patientName: string, password: string): Observable<boolean> {
    const authData = { patientName, password }; // Crée le body

    return this.http.post<boolean>(`${this.baseUrl}/auth`, authData).pipe(
      tap(response => console.log('Auth Response (boolean):', response)),
      map(response => {
        if (response === true) {
          this.currentPatientNameSubject.next(patientName);
          return true;
        } else {
          return false;
        }
      }),
      catchError(error => {
        console.error('Auth Error:', error);
        return of(false);
      })
    );
  }

  getCurrentPatientName(): string {
    return this.currentPatientNameSubject.value;
  }


  updatePassword(newPatientName: string, newPassword: string): Observable<any> {
    const payload = { patientName: newPatientName, password: newPassword };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.put(`${this.baseUrl}/patient/updatePassword`, payload, { headers });
  }

  getPatientByName(name: string): Observable<Patient> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Patient>(`${this.baseUrl}/by-name`, { params });
  }

  getPatientGenderByName(name: string): Observable<string> {
    return this.http.get(`${this.baseUrl}/gender` + `/gender?name=${encodeURIComponent(name)}`, { responseType: 'text' });
  }

}

