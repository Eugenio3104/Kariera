export interface UserExam {
  id: number;
  examName: string;
  cfu: number;
  academicYear: number;
  isElective: boolean;
  grade: number | null;
  status: string;
  registrationDate: string | null;
  teacher: string;
  isSelected?: boolean;
}
