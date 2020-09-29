import { SplitCamelCasePipe } from './splitCamelCase.pipe';

describe('SplitCamelCasePipe', () => {
    let pipe: SplitCamelCasePipe;
    const mockCamelCasedString = 'camelCaseString';
    beforeEach(() => {
        pipe = new SplitCamelCasePipe();
    });
    it('it should split the string at capital letters', () => {
        expect(pipe.transform(mockCamelCasedString)).toEqual('camel Case String');
    });
});
