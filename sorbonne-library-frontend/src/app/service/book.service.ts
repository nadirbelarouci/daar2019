import {EventEmitter, Inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../model/book";

@Injectable({
    providedIn: 'root'
})
export class BookService {
    constructor(private http: HttpClient, @Inject("baseUrl") private baseUrl: string) {
    }

    public onSearch: EventEmitter<Book[]> = new EventEmitter();

    getBooks(): Observable<Book[]> {
        return this.onSearch;
    }

    search(value: string) {
        let result: Observable<Book[]>;
        if (value.startsWith("regex:")) {
            result = this.searchRegex(value.replace("regex:", ""));
        } else {
            result = this.searchPrefix(value)
        }
        result.subscribe(books => this.onSearch.emit(books));

    }

    private searchPrefix(key: string) {
        return this.http.get<Book[]>(this.baseUrl + 'api/search/' + key);
    }

    private searchRegex(key: string) {
        return this.http.get<Book[]>(this.baseUrl + 'api/search/' + key);

    }

    uploadBook(book: File): Observable<any> {
        return this.http.post(this.baseUrl + 'api/books', book);
    }
}
