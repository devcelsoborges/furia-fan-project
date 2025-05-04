import { Topic } from './topic.model';
import { Post } from './post.model';

export interface TopicDetail {
  topic: Topic;
  posts: Post[];
}