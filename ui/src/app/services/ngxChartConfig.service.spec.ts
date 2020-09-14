import { TestBed } from '@angular/core/testing';
import { NgxChartConfigService } from './ngxChartConfig.service';

describe('ngxChartConfiguration', () => {
    let ngxChartConfigService: NgxChartConfigService;
    const mockVerticalBarConfig = {
        barPadding: 1,
        yAxisLabel: 'test'
    };
    const mockColorScheme = {
        domain: ['test']
    };

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [NgxChartConfigService]
        });
        ngxChartConfigService = TestBed.get(NgxChartConfigService);
    });

    it('should get barPadding', () => {
        ngxChartConfigService.verticalBarChartConfigs = mockVerticalBarConfig;
        expect(ngxChartConfigService.verticalBarChartPadding).toEqual(1);
    });

    it('should get barPadding', () => {
        ngxChartConfigService.verticalBarChartConfigs = mockVerticalBarConfig;
        expect(ngxChartConfigService.verticalBarChartYLabel).toEqual('test');
    });

    it('should get barPadding', () => {
        ngxChartConfigService.chartColorScheme = mockColorScheme;
        expect(ngxChartConfigService.colorScheme).toEqual({
            domain: ['test']
        });
    });
});
