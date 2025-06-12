import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FamilyAppointmentComponent } from './family-appointment.component';

describe('FamilyAppointmentComponent', () => {
  let component: FamilyAppointmentComponent;
  let fixture: ComponentFixture<FamilyAppointmentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FamilyAppointmentComponent]
    });
    fixture = TestBed.createComponent(FamilyAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
