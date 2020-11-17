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
    this.paginatorList = Array(this.paginatorLength).join('_').split('_').map((_, i) => i);
  }

  pageItemOnClick(index: number) {
    this.pageSelected.emit(index);
  }
}
