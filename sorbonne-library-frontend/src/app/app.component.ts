import {Component, OnInit} from '@angular/core';
import {MediaObserver} from '@angular/flex-layout';
import {BookService} from "./service/book.service";

@Component({
    selector: 'sl-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    constructor(public media: MediaObserver, private bookService: BookService) {
    }

    ngOnInit(): void {
    }

    search($event: string) {
        this.bookService.search($event);
    }
}
