import { Component, OnInit, OnDestroy } from '@angular/core';
import { DoctorService } from '../../../services/doctor.service'; // Import DoctorService
import { Subscription } from 'rxjs';
import {Patient} from '../../Patient/patient.model'

@Component({
  selector: 'app-doctor-area',
  templateUrl: './doctor-area.component.html',
  styleUrls: ['./doctor-area.component.css']
})

export class DoctorAreaComponent implements OnInit, OnDestroy {

  doctorName: string = '';
  patients: Patient[] = [];
  patientsPerPage = 5;
  currentPage = 1;

  private doctorNameSubscription: Subscription | undefined;
  isLoading = true;

  constructor(private doctorService: DoctorService) { } // Inject DoctorService

  ngOnInit(): void {
    this.doctorNameSubscription = this.doctorService.currentDoctorName$.subscribe(
      (name) => {
        this.doctorName = name;
        console.log('Doctor Name:', this.doctorName);

        // Fetch the patients for the doctor after the doctorName is available
        this.getPatientsForDoctor();
      }
    );

    setTimeout(() => {
      this.isLoading = false;
      // Optionally, navigate to another route here using Router
    }, 5000); // Delay duration in ms
  }

getPatientsForDoctor(): void {
    this.doctorService.getPatientsForDoctor(this.doctorName).subscribe(
      (patients: Patient[]) => {
        this.patients = patients;
        console.log('Patients:', patients);
      },
      (error) => {
        console.error('Error fetching patients:', error);
      }
    );
  }

  getVisitDates(visits: { [date: string]: string }): string[] {
    return Object.keys(visits);  // Returns the list of dates (keys) from the visits object
  }
  ngOnDestroy(): void {
    if (this.doctorNameSubscription) {
      this.doctorNameSubscription.unsubscribe(); // Prevent memory leaks
    }
  }

  get totalPages(): number {
    return Math.ceil(this.patients.length / this.patientsPerPage);
  }

  paginatedPatients() {
    const startIndex = (this.currentPage - 1) * this.patientsPerPage;
    return this.patients.slice(startIndex, startIndex + this.patientsPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }


}

