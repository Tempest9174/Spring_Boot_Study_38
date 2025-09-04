package raisetech.student.management2.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;
import raisetech.student.management2.service.StudentService;
import raisetech.student.management2.domain.StudentDetail;

/**
 * ダッシュボード機能を提供するコントローラー
 * フロントエンド向けのページレンダリングを担当
 */
@Controller
public class DashBoardController {

  private final StudentService studentService;

  @Autowired
  public DashBoardController(StudentService studentService) {
    this.studentService = studentService;
  }

  /**
   * ダッシュボードページを表示する
   * @param model テンプレートに渡すデータモデル
   * @return ダッシュボードテンプレート名
   */
  @Operation(summary = "ダッシュボード画面表示", description = "受講生管理のダッシュボード画面を表示します")
  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    return "dashboard";
  }

  /**
   * トップページ（ダッシュボードにリダイレクト）
   * @return ダッシュボードへのリダイレクト
   */
  @GetMapping("/")
  public String home() {
    return "redirect:/dashboard";
  }

  /**
   * 受講生一覧ページを表示する（既存のThymeleafページ）
   * @param model テンプレートに渡すデータモデル
   * @return 受講生一覧テンプレート名
   */
  @GetMapping("/students")
  public String studentList(Model model) {
    model.addAttribute("studentList", studentService.searchStudentList());
    return "studentList";
  }

  /**
   * 受講生更新ページを表示する
   * @param model テンプレートに渡すデータモデル
   * @return 受講生更新テンプレート名
   */
  @GetMapping("/update")
  public String updateStudentPage(Model model) {
    return "updateStudent";
  }

  /**
   * 受講生登録ページを表示する
   * @param model テンプレートに渡すデータモデル
   * @return 受講生登録テンプレート名
   */
  @GetMapping("/register")
  public String registerStudentPage(Model model) {
    return "registerStudent";
  }

  /**
   * API: 受講生検索（フィルタリング機能付き）
   * @param name 名前での絞り込み
   * @param email メールアドレスでの絞り込み
   * @param area 地域での絞り込み
   * @param minAge 最小年齢
   * @param maxAge 最大年齢
   * @param sex 性別での絞り込み
   * @param course コース名での絞り込み
   * @return フィルタリングされた受講生詳細リスト
   */
  @Operation(summary = "受講生検索API", description = "条件指定による受講生の絞り込み検索を行います")
  @GetMapping("/api/students/search")
  @ResponseBody
  public ResponseEntity<List<StudentDetail>> searchStudents(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String area,
      @RequestParam(required = false) Integer minAge,
      @RequestParam(required = false) Integer maxAge,
      @RequestParam(required = false) String sex,
      @RequestParam(required = false) String course) {

    List<StudentDetail> allStudents = studentService.searchStudentList();

    List<StudentDetail> filteredStudents = allStudents.stream()
        .filter(studentDetail -> {
          var student = studentDetail.getStudent();
          var courses = studentDetail.getStudentsCourseList();

          // 名前でのフィルタリング
          if (name != null && !name.isEmpty()) {
            if (student.getName() == null ||
                !student.getName().toLowerCase().contains(name.toLowerCase())) {
              return false;
            }
          }

          // メールアドレスでのフィルタリング
          if (email != null && !email.isEmpty()) {
            if (student.getEmail() == null ||
                !student.getEmail().toLowerCase().contains(email.toLowerCase())) {
              return false;
            }
          }

          // 地域でのフィルタリング
          if (area != null && !area.isEmpty()) {
            if (student.getArea() == null ||
                !student.getArea().toLowerCase().contains(area.toLowerCase())) {
              return false;
            }
          }

          // 年齢でのフィルタリング
          if (minAge != null && student.getAge() < minAge) {
            return false;
          }
          if (maxAge != null && student.getAge() > maxAge) {
            return false;
          }

          // 性別でのフィルタリング
          if (sex != null && !sex.isEmpty()) {
            if (student.getSex() == null || !student.getSex().equals(sex)) {
              return false;
            }
          }

          // コース名でのフィルタリング
          if (course != null && !course.isEmpty()) {
            if (courses == null || courses.isEmpty()) {
              return false;
            }
            boolean hasCourse = courses.stream()
                .anyMatch(c -> c.getCourseName() != null &&
                    c.getCourseName().toLowerCase().contains(course.toLowerCase()));
            if (!hasCourse) {
              return false;
            }
          }

          return true;
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(filteredStudents);
  }
}