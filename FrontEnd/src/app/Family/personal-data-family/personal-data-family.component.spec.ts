import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDataFamilyComponent } from './personal-data-family.component';

describe('PersonalDataFamilyComponent', () => {
  let component: PersonalDataFamilyComponent;
  let fixture: ComponentFixture<PersonalDataFamilyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonalDataFamilyComponent]
    });
    fixture = TestBed.createComponent(PersonalDataFamilyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
