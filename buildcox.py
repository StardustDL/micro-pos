from pathlib import Path
import os
from coxbuild.schema import task, group, named, run as prun, depend, ext, withExecutionState, ExecutionState, withProject, ProjectSettings, withConfig, Configuration


root = Path(".")


def ensureDir(dir: Path):
    if dir.exists() and dir.is_dir():
        return
    os.makedirs(dir)


@withConfig
@task
def create(config: Configuration):
    name: str = config.get("name") or ""
    if not name:
        print("No name specified")
        return
    subroot = root / name
    ensureDir(subroot)
    Path(subroot / "pom.xml").write_text(
        Path("./build/pom.xml").read_text().replace(r"{project}", name))

    Name = name.title()
    src = subroot / "src"
    ensureDir(src / "main" / "java" / "com" / "micropos" / name)
    Path(src / "main" / "java" / "com" / "micropos" / name / f"{Name}Application.java").write_text(
        Path("./build/entrypoint.java").read_text().replace(r"{project}", name).replace(r"{Project}", Name))
    ensureDir(src / "resources")


@withConfig
@task
def run(config: Configuration):
    name: str = config.get("name") or ""
    if not name:
        print("No name specified")
        return
    prun(["mvn", "spring-boot:run"], cwd=root / name, shell=True)


@withConfig
@task
def compile(config: Configuration):
    name: str = config.get("name") or ""
    if not name:
        for subdir in root.iterdir():
            if subdir.name == "build":
                continue
            if subdir.is_dir() and (subdir / "pom.xml").exists():
                prun(["mvn", "compile"], cwd=subdir, shell=True)
    else:
        prun(["mvn", "compile"], cwd=root / name, shell=True)
