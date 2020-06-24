// import {} from './employee-filter.pipe';
import {FirstWordPipe} from './first-word.pipe';
// import {AuthorModel} from '../models/author.model';

describe('FirstWordPipe', () => {
    let pipe: FirstWordPipe;
    const mockInputString = 'first second';
    beforeEach(() => {
        pipe = new FirstWordPipe();
    });
    it('it should return first word of string', () => {
        expect(pipe.transform(mockInputString)).toEqual('first');
    });
});
