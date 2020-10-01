import { TestBed } from '@angular/core/testing';
import { CommonService } from './common.service';

describe('CommonAppConfiguration', () => {
    let commonAppConfigService: CommonService;
    const mockVerticalBarConfig = {
        barPadding: 1,
        yAxisLabel: 'test'
    };
    const mockColorScheme = {
        domain: ['test']
    };

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [CommonService]
        });
        commonAppConfigService = TestBed.get(CommonService);
    });

    it('should get barPadding', () => {
        commonAppConfigService.verticalBarChartConfigs = mockVerticalBarConfig;
        expect(commonAppConfigService.verticalBarChartPadding).toEqual(1);
    });

    it('should get barPadding', () => {
        commonAppConfigService.verticalBarChartConfigs = mockVerticalBarConfig;
        expect(commonAppConfigService.verticalBarChartYLabel).toEqual('test');
    });

    it('should get barPadding', () => {
        commonAppConfigService.chartColorScheme = mockColorScheme;
        expect(commonAppConfigService.colorScheme).toEqual({
            domain: ['test']
        });
    });

    it('should get numberOfItemsInHallOfFamePage', () => {
        commonAppConfigService.numberOfItemsInHallOfFame = 10;
        expect(commonAppConfigService.getNumberOfItemsInHallOfFame).toEqual(10);
    });

    it('should get list on months', () => {
        commonAppConfigService.months = ['january', 'february', 'march'];
        expect(commonAppConfigService.monthList.length).toEqual(3);
    });

    it('should get name of the month for the the date provided', () => {
        commonAppConfigService.months = ['january', 'february', 'march'];
        const date = new Date();
        date.setMonth(0);
        expect(commonAppConfigService.getMonthName(date)).toEqual(commonAppConfigService.months[0]);
    });
});
