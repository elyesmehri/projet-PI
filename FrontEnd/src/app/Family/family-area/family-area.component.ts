import {Component, OnInit, OnDestroy, Input, TemplateRef} from '@angular/core';
import { FamilyService } from '../../../services/family.service'; // Import DoctorService
import { Subscription } from 'rxjs';
import {Family} from "../family.model";

@Component({
  selector: 'app-family-area',
  templateUrl: './family-area.component.html',
  styleUrls: ['./family-area.component.css']
})

export class FamilyAreaComponent implements OnInit, OnDestroy {

  Our_family: Family | undefined;

  familyName: string = '';
  currentTime: string = '';

  incrementedGetFamily: Family | null = null;
  errorMessageIncrementGet: string = '';

  private familyNameSubscription: Subscription | undefined;

  constructor(private familyService: FamilyService) {
  } // Inject DoctorService


  ngOnInit(): void {

    this.familyNameSubscription = this.familyService.currentFamilyName$.subscribe(
      (name) => {
        this.familyName = name;
        console.log('Family Name:', this.familyName);
      }
    );

    this.familyService.getFamily(this.familyName).subscribe({
      next: (family) => {
        this.Our_family = family;
      },
      error: (error) => {
        console.log("There is no such family sorry ...");
      }
    });

    this.familyService.incrementLogin(this.familyName).subscribe({
      next: (family) => {
        this.incrementedGetFamily = family;
      },
      error: (error) => {
        this.errorMessageIncrementGet = 'Erreur lors de l\'incrémentation et de la récupération du login.';
        console.error('Erreur d\'incrémentation et de récupération:', error);
      }
    });


    this.updateTime(); // Call once on init
    setInterval(() => this.updateTime(), 1000); // Update every second
  }

  updateTime(): void {
    const now = new Date();
    this.currentTime = now.toLocaleString(); // Or use toLocaleTimeString() / toISOString() as needed
  }

  ngOnDestroy(): void {
  }

}
