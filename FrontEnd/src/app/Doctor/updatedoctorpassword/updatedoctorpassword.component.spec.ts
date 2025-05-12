import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatedoctorpasswordComponent } from './updatedoctorpassword.component';

describe('UpdatedoctorpasswordComponent', () => {
  let component: UpdatedoctorpasswordComponent;
  let fixture: ComponentFixture<UpdatedoctorpasswordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdatedoctorpasswordComponent]
    });
    fixture = TestBed.createComponent(UpdatedoctorpasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
