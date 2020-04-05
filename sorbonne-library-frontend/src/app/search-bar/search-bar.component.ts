import {AfterViewInit, Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {fromEvent, Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, takeUntil, tap} from "rxjs/operators";
import {debounce} from "lodash";

@Component({
    selector: 'sl-search-bar',
    templateUrl: './search-bar.component.html',
    styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit,AfterViewInit {
    @ViewChild('search', {static: false}) private search: ElementRef;
    private unsubscribe: Subject<void> = new Subject();
    private handleSearch = null;
    @Output('onSearch') public onChange: EventEmitter<string> = new EventEmitter();

    constructor() {
    }
    ngOnInit() {
        this.handleSearch = debounce(() => {
            setTimeout(() => {
                this.onChange.emit(this.search.nativeElement.value);
            });
        }, 100);
    }

    ngAfterViewInit(): void {
        this.getInputEvent(this.search);
    }


    private getInputEvent(element: ElementRef) {
        fromEvent(element.nativeElement, 'keyup')
            .pipe(
                debounceTime(300),
                distinctUntilChanged(),
                tap(() => {
                    this.handleSearch();
                })
            )
            .pipe(takeUntil(this.unsubscribe))
            .subscribe();
    }

}
