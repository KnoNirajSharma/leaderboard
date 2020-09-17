import { EmployeeFilterPipe } from './employee-filter.pipe';
import { AuthorModel } from '../models/author.model';

describe('EmployeeFilterPipe', () => {
  let pipe: EmployeeFilterPipe;
  const dummyAuthorData: AuthorModel[] = [
    {
      knolderId: 1,
      knolderName: 'mark',
      allTimeScore: 10,
      allTimeRank: 2,
      quarterlyStreak: '5-6-7',
      monthlyScore: 7,
      monthlyRank: 1
    }, {
      knolderId: 2,
      knolderName: 'sam',
      allTimeScore: 10,
      allTimeRank: 2,
      quarterlyStreak: '5-6-7',
      monthlyScore: 7,
      monthlyRank: 1
    }
  ];
  beforeEach(() => {
    pipe = new EmployeeFilterPipe();
  });
  it('it should show filtered list of employees', () => {
    expect(pipe.transform(dummyAuthorData, 'mark')).toEqual([dummyAuthorData[0]]);
  });
  it('it should show list of employees', () => {
    expect(pipe.transform(dummyAuthorData, '')).toEqual(dummyAuthorData);
  });
});
