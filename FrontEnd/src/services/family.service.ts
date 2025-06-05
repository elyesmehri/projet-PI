import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, of, tap, throwError} from 'rxjs';
import {CheckFamilyRequest, Family, FamilyRequest} from "../app/Family/family.model";
import {AppointmentResponse} from "../app/Appointment/appointment.model";
import {UpdatePasswordRequest} from "../app/Family/family.model";
import {Patient} from "../app/Patient/patient.model";

class FamilyUpdateRequest {
}

@Injectable({
  providedIn: 'root'
})

export class FamilyService {

  private baseUrl = 'http://localhost:8082/family';  // Update if needed
  private currentFamilyNameSubject = new BehaviorSubject<string>(''); // BehaviorSubject
  public currentFamilyName$ = this.currentFamilyNameSubject.asObservable(); // Observable

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {}

  // Create Family
  createFamily(family: {

    familyname: string;
    password: string;
    relative : string;
    relationship : string;
    address: string;
    phoneNumber: string;
    advice : string;
    invest : number;

  }): Observable<Family> {
    return this.http.post<Family>(`${this.baseUrl}/create`, family, this.httpOptions);
  }

  getCurrentFamilyName(): string {
    return this.currentFamilyNameSubject.value;
  }

  setFamilyName(familyname: string): void {
    this.currentFamilyNameSubject.next(familyname);
    console.log(`Family name set in service: ${familyname}`);
  }

  // Get All Families (assuming this endpoint exists)
  getAllFamilies(): Observable<Family[]> {
    return this.http.get<Family[]>(`${this.baseUrl}/all`);
  }

  // Get Family by ID (assuming this endpoint exists)
  getFamilyById(id: number): Observable<Family> {
    return this.http.get<Family>(`${this.baseUrl}/${id}`);
  }

  // Update Family Investissement
  updateFamilyInvestissement(id: number, investissement: string): Observable<Family> {
    return this.http.put<Family>(`${this.baseUrl}/${id}/invest`, { investissement }, this.httpOptions);
  }

  // Update Family Advice
  updateFamilyAdvice(id: number, advice: string): Observable<Family> {
    return this.http.put<Family>(`${this.baseUrl}/families/${id}/advice`, { advice }, this.httpOptions);
  }

  // Get all Appointments for a specific Family
  getFamilyAppointments(familyId: number): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.baseUrl}/${familyId}/appointments`);
  }

  incrementLogin(familyId: number): Observable<Family> {
    const url = `${this.baseUrl}/login/${familyId}`;
    return this.http.post<Family>(url, {}); // POST request avec un corps vide
  }

  resetLogin(familyId: number): Observable<Family> {
    const url = `${this.baseUrl}/resetlogin/${familyId}`;
    return this.http.post<Family>(url, {}); // POST request avec un corps vide
  }

  // Get Family id by his name
  getFamilyIdByName(familyname: string): Observable<number> {
    // Note: Use encodeURIComponent if the name might contain special characters or spaces
    return this.http.get<number>(`${this.baseUrl}/id-by-name?name=${encodeURIComponent(familyname)}`);
  }

  // Check Family Exists (login-like functionality)
  checkFamilyExists(credentials: CheckFamilyRequest): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseUrl}/auth`, credentials, this.httpOptions);
  }

  // Update Family Password
  updateFamilyPassword(familyId: number, newPassword: string): Observable<string> {
    // 1. Create the request body object
    const requestBody: UpdatePasswordRequest = { newPassword: newPassword };

    // 2. Construct the URL to match the secure backend endpoint: /api/doctors/{id}/password
    const url = `${this.baseUrl}/password/update/${familyId}?password=${encodeURIComponent(newPassword)}`;

    // 3. Send the PATCH request with the body
    return this.http.patch<string>(url, requestBody, this.httpOptions);
  }

  updateFamily(id: number, updateData: FamilyUpdateRequest): Observable<Family> {
    const url = `${this.baseUrl}/${id}`; // Constructs the URL like http://localhost:8082/patient/123
    console.log(`Sending PUT request to: ${url} with data:`, updateData);
    return this.http.put<Family>(url, updateData, this.httpOptions);
  }
}
