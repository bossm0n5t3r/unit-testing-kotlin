package crm

class CRMMainService(
    private val crmUserService: CRMUserService,
    private val crmCompanyService: CRMCompanyService,
    private val crmMessageBusService: CRMMessageBusService,
) {
    fun changeEmail(userId: Long, newEmail: String) {
        // 데이터베이스에서 사용자의 현재 이메일과 유형 검색
        val user = crmUserService.findById(userId) ?: return

        // 새 이메일의 도메인 이름에 따라 사용자 유형 설정
        val crmCompany = crmCompanyService.getCompany()
        if (crmCompany != null) {
            crmUserService.updateUser(user, newEmail, crmCompany)

            // 메시지 버스에 알림 전송
            crmMessageBusService.sendEmailChangedMessage(userId, newEmail)
        }
    }
}
