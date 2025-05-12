import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FamilyRegistrationComponent } from './family-registration.component';

describe('FamilyRegistrationComponent', () => {
  let component: FamilyRegistrationComponent;
  let fixture: ComponentFixture<FamilyRegistrationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FamilyRegistrationComponent]
    });
    fixture = TestBed.createComponent(FamilyRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
