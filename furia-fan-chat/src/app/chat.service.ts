import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Message } from './message/message.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private apiUrl = 'http://localhost:8080/api/chat';

  constructor(private http: HttpClient) { }

  getMessages(): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/messages`);
  }

  sendMessage(content: string): Observable<Message[]> {
    return this.http.post<Message[]>(`${this.apiUrl}/send`, { message: content });
  }

  uploadDocument(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(`${this.apiUrl}/upload-document`, formData, { responseType: 'text' });
  }
}
