import { Component } from '@angular/core';
import { DoctorService } from '../../../services/doctor.service';
import { Router } from '@angular/router';  // Import Router

@Component({
  selector: 'app-espace-doctor', //  Your selector
  templateUrl: './espace-doctor.component.html', // Your template URL
  styleUrls: ['./espace-doctor.component.css']  // Your styles URL
})

export class EspaceDoctorComponent {

  doctorname: string = '';
  password: string = '';
  errorMessage: string = '';
  loginFailed: boolean = false;
  loginSuccess: boolean = false;

  constructor(private doctorService: DoctorService, private router: Router) {}

    login() {

      console.log ("doctor name : " + this.doctorname);
      console.log ("password : " + this.password);

      this.doctorService.Login(this.doctorname, this.password).subscribe(
        (success) => {
          if (success) {
            this.router.navigate(['/doctorarea']);
          } else {
            this.errorMessage = 'Invalid credentials';
          }
        },
        (error) => {
          this.errorMessage = 'Error: ' + error.message;
        }
      );
    }

  }
