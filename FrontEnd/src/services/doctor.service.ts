import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {BehaviorSubject, catchError, map, Observable, of, tap} from 'rxjs';
import { Doctor } from '../app/Doctor/doctor.model';
import {Patient} from "../app/Patient/patient.model";

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

  private baseUrl = 'http://localhost:8085/doctor';
  private currentDoctorNameSubject = new BehaviorSubject<string>(''); // BehaviorSubject
  public currentDoctorName$ = this.currentDoctorNameSubject.asObservable(); // Observable

  constructor(private http: HttpClient) { }

  getAll(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.baseUrl}/all`);
  }

  addOne(doctor: {
    doctorname: string;
    speciality: string;
    address: string;
    score: number;
    phonenumber: string;
    insurance: string;
    hospital: string;
    numberofpatients: number;
    password: string
  }): Observable<Doctor> {
    return this.http.post<Doctor>(`${this.baseUrl}/add`, doctor);
  }

  Login(doctorname: string, password: string): Observable<boolean> {
    const authData = { doctorname, password }; // Crée le body

    return this.http.post<boolean>(`${this.baseUrl}/auth`, authData).pipe(
      tap(response => console.log('Auth Response (boolean):', response)),
      map(response => {
        if (response === true) {
          this.currentDoctorNameSubject.next(doctorname);
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

  getCurrentDoctorName(): string {
    return this.currentDoctorNameSubject.value;
  }


  updatePassword(doctorname: string, newPassword: string): Observable<any> {
    const payload: UpdatePasswordPayload = {
      doctorname: doctorname,
      password: newPassword,
    };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    // Assuming your backend updatePassword endpoint is a PUT request
    return this.http.put(`${this.baseUrl}/updatePassword`, payload, { headers });
  }

  getPatientsForDoctor(doctorName: string): Observable<Patient[]> {
    let params = new HttpParams(); // Create HttpParams object
    params = params.append('doctorName', doctorName); // Add the parameter

    return this.http.get<Patient[]>(`${this.baseUrl}/patients`, { params: params }); // Pass params in the request
  }

  getDoctorById(id: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.baseUrl}/${id}`);
  }
}
