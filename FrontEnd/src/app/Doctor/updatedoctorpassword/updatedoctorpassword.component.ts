import { Component } from '@angular/core';
import {DoctorService} from '../../../services/doctor.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-updatedoctorpassword',
  templateUrl: './updatedoctorpassword.component.html',
  styleUrls: ['./updatedoctorpassword.component.css']
})

export class UpdatedoctorpasswordComponent {

  doctorname: string = '';
  newPassword: string = '';
  message: string = '';

  // Regex: Min 8 chars, 1 uppercase, 1 lowercase, 1 special char
  passwordPattern: string = '^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$';

  constructor(private doctorService: DoctorService) {}

  onUpdatePassword(): void {
    this.doctorService.updatePassword(this.doctorname, this.newPassword).subscribe({
      next: (res) => this.message = '✅ Password updated successfully.',
      error: () => this.message = ''
    });
  }
}
