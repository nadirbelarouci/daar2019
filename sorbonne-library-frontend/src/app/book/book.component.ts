import {Component, Input, OnInit} from '@angular/core';
import {Book} from "../model/book";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
    selector: 'sl-book',
    templateUrl: './book.component.html',
    styleUrls: ['./book.component.css']
})

export class BookComponent implements OnInit {
    @Input() book: Book;
    coverUrl: SafeResourceUrl;

    constructor(private sanitizer: DomSanitizer) {
    }

    ngOnInit() {
        const url = `https://www.gutenberg.org/cache/epub/${this.book.bookId}/pg${this.book.bookId}.cover.medium.jpg`;

        this.coverUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);

    }

}
