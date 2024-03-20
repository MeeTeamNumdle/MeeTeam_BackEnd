package synk.meeteam.domain.user.user.dto.command;

public record UpdateInfoCommand(
        String name,
        String pictureUrl,
        String subEmail,
        boolean isPublicSubEmail,
        boolean isPublicSchoolEmail,
        boolean isSchoolMain,
        String phoneNumber,
        boolean isPublicPhone,
        String oneLineIntroduction,
        String mainIntroduction,
        double gpa,
        double maxGpa,
        Long interest_id) {

}
