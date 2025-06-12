import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorApppointmentComponent } from './doctor-apppointment.component';

describe('DoctorApppointmentComponent', () => {
  let component: DoctorApppointmentComponent;
  let fixture: ComponentFixture<DoctorApppointmentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DoctorApppointmentComponent]
    });
    fixture = TestBed.createComponent(DoctorApppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
