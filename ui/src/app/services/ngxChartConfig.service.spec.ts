import { TestBed } from '@angular/core/testing';
import { NgxChartConfigService } from './ngxChartConfig.service';

describe('ngxChartConfiguration', () => {
    let ngxChartConfigService: NgxChartConfigService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [NgxChartConfigService]
        });
        ngxChartConfigService = TestBed.get(NgxChartConfigService);
    });

    it('should be created', () => {
        expect(ngxChartConfigService).toBeTruthy();
    });

    it('should get barPadding', () => {
        expect(ngxChartConfigService.verticalBarChartPadding).toEqual(10);
    });

    it('should get barPadding', () => {
        expect(ngxChartConfigService.verticalBarChartYLabel).toEqual('score');
    });

    it('should get barPadding', () => {
        expect(ngxChartConfigService.colorScheme).toEqual({
            domain: ['#2C42A5', '#4CA52C', '#C7AD05', '#224A4B', '#0B8D84', '#2CA1A5', '#2F3640', '#4B0082']
        });
    });
});
