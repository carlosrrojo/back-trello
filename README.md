
# Backend - trello

This backend is a **Spring Boot** REST API that powers a **Kanban/trello-like** application.
It provides authentication, workspace/project management, kanban board operations, issue tracking, comments, attachments and auditing.

The backend is designed to be:
- **Secure** (JWT authentication + role-based permissions)
- **Scalable** (clean layered architecture + DTOs + pagination)
- **Frontend-friendly** (REST endpoints aligned with Angular UI needs

---

## What the Backend Provides

### 1) Authentication & Security
The API uses:
- **JWT Access Token** for authenticated requests
- **Refresh Token** for renewing sessions without re-login
-  Passowrd hashing with **BCrypt**
-  Role-based authorization for workspaces and projedcts

**Main features**
- Register user
- Login user
- Refresh session
- Logout (invalidate refresh token)

---

### 2) Workspaces & Memberships
Workspaces represent an organization/team area where projects live.

**Workspace capabilities**
- Create workspace
- List workspaces for current user
- Manage workspace members
- Invite users to a workspace (token-based invitation)

** Roles (Workspace-level)**
- `OWNER`
- `ADMIN`
- `MEMBER`
- `VIEWER`

---

### 3) Projects & Projects Roles
Projects belogn to a workspace and contain a Kanban board.

**Project capabilities**
- Create project under a workspace
- Manage project members and roles
- Retrieve project details
- Update project metadata

** Roles (Project-level)**
- `ADMIN`
- `MEMBER`
- `VIEWER`

---

## Kanban Board Logic

### 4) Board Columns
Each project has a set of ordered columns.

**Column capabilities**
- Create column
- Update column name
- Delete column (optional: move isssues elsewhere first)
- Reorder columns

---

### 5) Issues (Core Domain)
Issues are the main unit of work.

**Issue fields**
- `title` (required)
- `description` (markdown supported)
- `type`: `TASK | BUG | STORY`
- `priority`: `LOW | MEDIUM | HIGH`
- `dueDate`
- `columnId`
- `position`

  **Issue capabilities**
  - Create issue in a column
  - Edit issue details
  - Move issue across columns (drag & drop support)
  - Reorder issues within the same column
  - Assgin members

  ---

  ### 6) Comments
  Issues support comments for collaboration

  **Comment capabilities**
  - Create comment
  - List comments by issue

  ---

  ### Architecture Overview

  ### Layered Structure
  The backend follows a clean layered approach:

  - **Controller layer**
    - REST endpoints
    - Request validation
    - DTO mapping

  - **Service layer**
    - Business rules
    - Permission cjhecks
    - Transactions

  - **Repository layer**
    - Database accesss

  - **Domain model**
    - Entities (User, Workspace, Project, Issue...)

  ---

  ## Database Model (Core Entities)
  Minimal core entities:

  - `User`
  - `Workspace`
  - `WorkspaceMember`
  - `Project`
  - `ProjectMember`
  - `BoardColumn`
  - `Issue`
  - `IssueAssignee`
  - `Comment`

  ---

  ## API Endpoints (High Level)

  ### Auth
  - `POST /api/auth/register`
  - `POST /api/auth/login`
  - `POST /api/auth/refresh`
  - `POST /api/auth/logout`

  ### Workspaces
  - `GET /api/workspaces`
  - `POST /api/workspaces`
  - `GET /api/workspaces/{workspaceId}`
  - `POST /api/workspaces/{workspaceId}/invites`
  - `PATCH /api/workspaces/{workspaceId}/members/{userId}`

  ### Projects
  - `GET /api/workspaces/{workspaceId}/projects`
  - `POST /api/workspaces/{workspaceId}/projects`
  - `GET /api/projects/{projectsId}`
  - `PATCH /api/projects/{projectId}`
  - `POST /api/projects/{projectId}/members`

  ### Board Columns
  - `GET /api/projects/{projectId}/board`
  - `POST /api/projects/{projectId}/columns`
  - `PATCH /api/columns/{columnId}`
  - `DELETE /api/columns/{columnId}`
  - `POST /api/projects/{projectsId}/columns/reorder`

  ### Issues
  - `GET /api/projects/{projectId}/issues`
  - `POST /api/projects/{projectId}/issues`
  - `GET /api/issues/{issueId}`
  - `PATCH /api/issues/{issueId}`
  - `POST /api/issues/{issueId}/move`
  - `POST /api/issues/{issueId}/assignees`
  - `DELETE /api/issues/{issueId}/assignees/{userId}`

  ### Comments
  - `GET /api/issues/{issueId}/comments`
  - `POST /api/issues/{issueId}/comments`

  

