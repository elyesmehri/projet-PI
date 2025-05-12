import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientAreaComponent } from './patient-area.component';

describe('PatientAreaComponent', () => {
  let component: PatientAreaComponent;
  let fixture: ComponentFixture<PatientAreaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PatientAreaComponent]
    });
    fixture = TestBed.createComponent(PatientAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
