import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuildingplotDetailComponent } from './buildingplot-detail.component';

describe('LandDetailsComponent', () => {
  let component: BuildingplotDetailComponent;
  let fixture: ComponentFixture<BuildingplotDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuildingplotDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BuildingplotDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
