import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-list-paginator',
  templateUrl: './list-paginator.component.html',
  styleUrls: ['./list-paginator.component.scss'],
})
export class ListPaginatorComponent implements OnInit {
  @Input() paginatorLength: number;
  @Input() currentPage: number;
  @Output() pageSelected = new EventEmitter();
  paginatorList = [];

  ngOnInit() {
    // this.paginatorList = [...Array(this.paginatorLength).keys()];
    this.paginatorList = new Array(this.paginatorLength);
    this.paginatorList.map((_, index) => index);
    console.log(this.paginatorList);
  }

  pageItemOnClick(index: number) {
    this.pageSelected.emit(index);
  }
}
