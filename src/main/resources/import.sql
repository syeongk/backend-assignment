-- 할인 정책 데이터 --
INSERT INTO discount_policy (name, discount_value, discount_unit, discount_type, is_deleted, version, start_at, created_at, updated_at) VALUES ('NORMAL 등급 할인', 0, 'WON', 'GRADE', false, 1, NOW(), NOW(), NOW());
INSERT INTO discount_policy (name, discount_value, discount_unit, discount_type, is_deleted, version, start_at, created_at, updated_at) VALUES ('VIP 등급 할인', 1000, 'WON', 'GRADE', false, 1, NOW(), NOW(), NOW());
INSERT INTO discount_policy (name, discount_value, discount_unit, discount_type, is_deleted, version, start_at, created_at, updated_at) VALUES ('VVIP 등급 할인', 10, 'PERCENT', 'GRADE', false, 1, NOW(), NOW(), NOW());
INSERT INTO discount_policy (name, discount_value, discount_unit, discount_type, is_deleted, version, start_at, created_at, updated_at) VALUES ('포인트 추가 중복 할인', 5, 'PERCENT', 'POINT', false, 1, NOW(), NOW(), NOW());

-- 회원 등급 데이터 --
INSERT INTO grade (grade_type, discount_policy_id, created_at, updated_at, is_deleted) VALUES ('NORMAL', (SELECT id FROM discount_policy WHERE name = 'NORMAL 등급 할인'), NOW(), NOW(), false);
INSERT INTO grade (grade_type, discount_policy_id, created_at, updated_at, is_deleted) VALUES ('VIP', (SELECT id FROM discount_policy WHERE name = 'VIP 등급 할인'), NOW(), NOW(), false);
INSERT INTO grade (grade_type, discount_policy_id, created_at, updated_at, is_deleted) VALUES ('VVIP', (SELECT id FROM discount_policy WHERE name = 'VVIP 등급 할인'), NOW(), NOW(), false);

-- 회원 데이터 --
INSERT INTO member (name, point, grade_id, created_at, updated_at, is_deleted) VALUES ('김서영', 10000.00, (SELECT id FROM grade WHERE grade_type = 'NORMAL'), NOW(), NOW(), false);
INSERT INTO member (name, point, grade_id, created_at, updated_at, is_deleted) VALUES ('김소영', 10000.00, (SELECT id FROM grade WHERE grade_type = 'NORMAL'), NOW(), NOW(), false);
INSERT INTO member (name, point, grade_id, created_at, updated_at, is_deleted) VALUES ('박소영', 100000.00, (SELECT id FROM grade WHERE grade_type = 'VIP'), NOW(), NOW(), false);
INSERT INTO member (name, point, grade_id, created_at, updated_at, is_deleted) VALUES ('이소영', 1000000.00, (SELECT id FROM grade WHERE grade_type = 'VVIP'), NOW(), NOW(), false);

-- 아이템 데이터 --
INSERT INTO item (name, price, created_at, updated_at, is_deleted) VALUES ('무선 이어폰', 369000.00, NOW(), NOW(), false);
INSERT INTO item (name, price, created_at, updated_at, is_deleted) VALUES ('썰은 배추김치', 14900.00, NOW(), NOW(), false);
INSERT INTO item (name, price, created_at, updated_at, is_deleted) VALUES ('바하 클렌징폼', 6020.00, NOW(), NOW(), false);
INSERT INTO item (name, price, created_at, updated_at, is_deleted) VALUES ('팽이버섯', 830.00, NOW(), NOW(), false);
