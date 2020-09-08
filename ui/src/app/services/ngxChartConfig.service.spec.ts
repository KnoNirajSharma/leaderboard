import { TestBed } from '@angular/core/testing';
import { NgxChartConfigService } from './ngxChartConfig.service';

fdescribe('ngxChartConfiguration', () => {
    let ngxChartConfigService: NgxChartConfigService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [NgxChartConfigService]
        });
        ngxChartConfigService = TestBed.get(NgxChartConfigService);
        ngxChartConfigService.verticalBarChartConfigs = {
            barPadding: 1,
            yAxisLabel: 'test'
        };
        ngxChartConfigService.chartColorScheme = {
            domain: ['test']
        };
    });

    it('should get barPadding', () => {
        expect(ngxChartConfigService.verticalBarChartPadding).toEqual(1);
    });

    it('should get barPadding', () => {
        expect(ngxChartConfigService.verticalBarChartYLabel).toEqual('test');
    });

    it('should get barPadding', () => {
        expect(ngxChartConfigService.colorScheme).toEqual({
            domain: ['test']
        });
    });
});
