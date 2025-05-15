import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatePatientDataComponent } from './update-patient-data.component';

describe('UpdatePatientDataComponent', () => {
  let component: UpdatePatientDataComponent;
  let fixture: ComponentFixture<UpdatePatientDataComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdatePatientDataComponent]
    });
    fixture = TestBed.createComponent(UpdatePatientDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
