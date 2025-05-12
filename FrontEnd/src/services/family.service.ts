import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, of, tap, throwError} from 'rxjs';
import { Family } from "../app/Family/family.model";

interface AuthResponse {
  success?: boolean;
  error?: string;
  //  You might get a token or user info here on success
  //  token?: string;
  //  userId?: number;
  familyname?: string;
}

@Injectable({
  providedIn: 'root'
})
export class FamilyService {

  private apiUrl = 'http://localhost:8085/family';  // Update if needed
  private currentFamilyNameSubject = new BehaviorSubject<string>(''); // BehaviorSubject
  public currentFamilyName$ = this.currentFamilyNameSubject.asObservable(); // Observable


  constructor(private http: HttpClient) {}

  updateInvest(familyname: string, invest: number): Observable<Family> {
    const params = new HttpParams()
      .set('familyname', familyname)
      .set('invest', invest.toString());

    return this.http.put<Family>(`${this.apiUrl}/updateinvest`, null, { params });
  }

  checkFamilyExists (familyname: string, relative: string, password: string): Observable<boolean> {
    const params = new HttpParams()
      .set('familyname', familyname)
      .set('relative', relative)
      .set('password', password);

    return this.http.get<boolean>(`${this.apiUrl}/checkFieldsExist`, { params }).pipe(

      tap(response => console.log('Auth Response (boolean):', response)),
      map(response => {
        if (response === true) {
          this.currentFamilyNameSubject.next(familyname);
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

  // Register Family
  registerFamily(family: Family): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, family);
  }

  incrementLogin(familyName: string): Observable<Family> {
    const url = `${this.apiUrl}/login/${familyName}`;
    return this.http.post<Family>(url, {}); // POST request avec un corps vide
  }

  resetLogin(familyName: string): Observable<Family> {
    const url = `${this.apiUrl}/resetlogin/${familyName}`;
    return this.http.post<Family>(url, {}); // POST request avec un corps vide
  }

  getFamily(familyName: string): Observable<Family> {
    const url = `${this.apiUrl}/${familyName}`; // Construction de l'URL avec le familyName
    return this.http.get<Family>(url);
  }

  addNewFamily(family: {
    familyname: string;
    patientname: string;
    relative: string;
    relationship: string;
    address: string;
    phonenumber: string;
    insurance: string;
    password: string;
    invest: string;
  }): Observable<Family> {
    return this.http.post<Family>(`${this.apiUrl}/add`, family).pipe(
      catchError(error => {
        console.error('Error adding new family:', error);
        return throwError(() => error);
      })
    );
  }
}
