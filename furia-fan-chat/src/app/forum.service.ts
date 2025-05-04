import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, switchMap, of } from 'rxjs';
import { Topic } from './models/topic.model';
import { Post } from './models/post.model';
import { TopicDetail } from './models/topic-detail';

@Injectable({ providedIn: 'root' })
export class ForumService {
  private apiUrl = 'http://localhost:8080/api/forum';

  constructor(private http: HttpClient) {}

  criarTopico(topic: Omit<Topic, 'id' | 'dataPostagem'>): Observable<Topic> {
    return this.http.post<Topic>(this.apiUrl, topic);
  }

  listarTopicos(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl);
  }

  
  getTopic(topicId: number): Observable<TopicDetail | null> {
    
    const mockTopics: { [key: number]: TopicDetail } = {
      1: {
        topic: { id: 1, titulo: 'Discussão Geral sobre a FURIA', descricao: 'Descrição resumida...', autor: 'Admin', dataPostagem: '2023-10-26T10:00:00Z' },
        posts: [
          { id: 101, autor: 'Admin', mensagem: 'Olá a todos! Vamos discutir sobre o desempenho recente da FURIA?', dataHora: '2023-10-26T10:00:00Z', topico_id: 1 },
          { id: 102, autor: 'Fan01', mensagem: 'Achei que o último jogo foi incrível!', dataHora: '2023-10-26T10:15:00Z', topico_id: 1 },
        ]
      },
       2: {
        topic: { id: 2, titulo: 'Estratégias e Análises', descricao: 'Análises táticas...', autor: 'Coach', dataPostagem: '2023-10-25T09:00:00Z' },
        posts: [
          { id: 201, autor: 'Coach', mensagem: 'Analisando a estratégia no mapa X...', dataHora: '2023-10-25T09:00:00Z', topico_id: 2 },
        ]
      },
    };
     return of(mockTopics[topicId] || null);

   
  }


  listarPostsPorTopico(topicId: number): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/${topicId}/posts`);
  }

  criarPost(topicId: number, post: Omit<Post, 'id' | 'dataHora' | 'topico_id'>): Observable<Post> {
    return this.http.post<Post>(`${this.apiUrl}/${topicId}/posts`, post);
  }

  criarTopicoComPostInicial(topicData: Omit<Topic, 'id' | 'dataPostagem'>, postData: Omit<Post, 'id' | 'dataHora' | 'topico_id'>): Observable<Post> {
    return this.criarTopico(topicData).pipe(
      switchMap(novoTopico => {
        if (novoTopico.id) {
          return this.criarPost(novoTopico.id, postData);
        } else {
          throw new Error('ID do tópico não retornado pelo backend');
        }
      })
    );
  }
}