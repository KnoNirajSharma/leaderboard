import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { MenuBoxComponent } from './menu-box.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('MenuBoxComponent', () => {
  let component: MenuBoxComponent;
  let fixture: ComponentFixture<MenuBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuBoxComponent ],
      imports: [IonicModule.forRoot(), RouterTestingModule]
    }).compileComponents();

    fixture = TestBed.createComponent(MenuBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
