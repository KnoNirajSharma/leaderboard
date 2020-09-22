import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { BadgeDetailTableComponent } from './badge-detail-table.component';
import { CustomPipesModule } from '../../pipe/custom-pipes.module';

describe('BadgeDetailTableComponent', () => {
  let component: BadgeDetailTableComponent;
  let fixture: ComponentFixture<BadgeDetailTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BadgeDetailTableComponent ],
      imports: [IonicModule.forRoot(), CustomPipesModule]
    }).compileComponents();

    fixture = TestBed.createComponent(BadgeDetailTableComponent);
    component = fixture.componentInstance;
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
