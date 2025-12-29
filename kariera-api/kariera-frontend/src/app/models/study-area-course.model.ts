export interface Course {
  id: number;
  name: string;
  study_area_id: number;
}

export interface StudyArea {
  id: number;
  name: string;
}

export interface StudyAreaWithCourses {
  id: number;
  name: string;
  courses: Course[];
}
