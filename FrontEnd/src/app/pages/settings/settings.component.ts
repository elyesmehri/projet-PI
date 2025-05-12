import { Component } from '@angular/core';
import {DoctorService} from "../../../services/doctor.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})

export class SettingsComponent {

  doctors: any[] = [];

  constructor(private doctorService: DoctorService, private router: Router) {}


  ngOnInit() {
    this.doctorService.getAll().subscribe(data => {
      this.doctors = data;
    });
  }

  getStars(score: number): boolean[] {
    const normalized = Math.round((score / 100) * 5);
    return Array(5).fill(false).map((_, i) => i < normalized);
  }

  goToAppointment(id: number) {
    this.router.navigate(['/appointment', id]);
  }

}
